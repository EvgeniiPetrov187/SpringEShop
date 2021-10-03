import {Component, OnInit} from '@angular/core';
import {OrderService} from "../../services/order.service";
import {Order} from "../../model/order";

export const ORDERS_URL = 'order';

@Component({
  selector: 'app-order-page',
  templateUrl: './order-page.component.html',
  styleUrls: ['./order-page.component.scss']
})
export class OrderPageComponent implements OnInit {

  orders: Order[] = [];

  constructor(private orderService: OrderService) {
  }

  ngOnInit(): void {
    this.orderService.findOrdersByUser()
      .subscribe(orders => {
          this.orders = orders;
        },
        error => {
          console.log(error);
        })
  }
}
