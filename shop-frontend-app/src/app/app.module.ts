import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavBarComponent } from './components/nav-bar/nav-bar.component';
import { FooterComponent } from './components/footer/footer.component';
import { ProductGalleryComponent } from './components/product-gallery/product-gallery.component';
import { ProductGalleryPageComponent } from './pages/product-gallery-page/product-gallery-page.component';
import { ProductFilterComponent } from './components/product-filter/product-filter.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { ProductInfoPageComponent } from './pages/product-info-page/product-info-page.component';
import { CartPageComponent } from './pages/cart-page/cart-page.component';
import { LoginPageComponent } from './pages/login-page/login-page.component';
import { RegisterPageComponent } from './pages/register-page/register-page.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import { OrderPageComponent } from './pages/order-page/order-page.component';
import {UnauthorizedInterceptor} from "./helpers/unauthorized-interceptor";
import { PaginationComponent } from './components/pagination/pagination.component';

@NgModule({
  declarations: [
    AppComponent,
    NavBarComponent,
    FooterComponent,
    ProductGalleryComponent,
    ProductGalleryPageComponent,
    ProductFilterComponent,
    ProductInfoPageComponent,
    CartPageComponent,
    LoginPageComponent,
    RegisterPageComponent,
    OrderPageComponent,
    PaginationComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        FormsModule,
        HttpClientModule,
        ReactiveFormsModule
    ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: UnauthorizedInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
