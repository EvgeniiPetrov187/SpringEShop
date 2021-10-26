package com.petrov;

import com.petrov.persist.ProductRepository;
import com.petrov.persist.model.Brand;
import com.petrov.persist.model.Category;
import com.petrov.persist.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.ConsumerEndpointSpec;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.transformer.FileToStringTransformer;
import org.springframework.integration.jpa.dsl.Jpa;
import org.springframework.integration.jpa.dsl.JpaUpdatingOutboundEndpointSpec;
import org.springframework.integration.jpa.support.PersistMode;
import org.springframework.messaging.MessageHandler;

import javax.persistence.EntityManagerFactory;
import java.io.File;
import java.math.BigDecimal;

@Configuration
    public class ImportConfig {

        private static final Logger logger = LoggerFactory.getLogger(ImportConfig.class);

        @Value("${source.directory.path}")
        private String sourceDirPath;

        @Value("${dest.directory.path}")
        private String destDirPath;

        @Autowired
        private EntityManagerFactory entityManagerFactory;

        @Bean
        public MessageSource<File> sourceDirectory() {
            FileReadingMessageSource messageSource = new FileReadingMessageSource();
            messageSource.setDirectory(new File(sourceDirPath));
            messageSource.setAutoCreateDirectory(true);
            return messageSource;
        }

        @Bean
        public MessageHandler destDirectory() {
            FileWritingMessageHandler handler = new FileWritingMessageHandler(new File(destDirPath));
            handler.setExpectReply(false);
            handler.setDeleteSourceFiles(true);
            return handler;
        }

        @Bean
        public JpaUpdatingOutboundEndpointSpec jpaPersistHandler() {
            return Jpa.outboundAdapter(this.entityManagerFactory)
                    .entityClass(Product.class) // указать класс нужной сущности
                    .persistMode(PersistMode.PERSIST);
        }

        @Bean
        public IntegrationFlow fileMoveFlow() {
            return IntegrationFlows.from(sourceDirectory(), conf -> conf.poller(Pollers.fixedDelay(2000)))
                    .filter(msg -> ((File) msg).getName().endsWith(".txt"))
                    .transform(new FileToStringTransformer())
                    //.<String, String>transform(String::toUpperCase)
                    //.split(s -> s.delimiters("\n"))
                    .<String, Product>transform(str -> {
                        logger.info("New str {}", str);
                        return parseFile(str); // написать преобразование строки из файла в сущность
                    })
                    //.handle(destDirectory())
                    .handle(jpaPersistHandler(), ConsumerEndpointSpec::transactional)
                    .get();
        }

        public Product parseFile(String file){
            String[] parsing = file.split(",");
            Product product;
            try {
                product = new Product(null,
                        parsing[0],
                        BigDecimal.valueOf(Double.parseDouble(parsing[1])),
                        parsing[2],
                        new Category(Long.parseLong(parsing[3]), null),
                        new Brand(Long.parseLong(parsing[4]), null)
                        );
            } catch (RuntimeException e){
                logger.info("Error format file");
                return null;
            }
            return product;
        }
}
