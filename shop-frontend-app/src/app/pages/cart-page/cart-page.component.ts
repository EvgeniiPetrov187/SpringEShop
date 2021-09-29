import {Component, OnInit} from '@angular/core';
import {CartService} from "../../services/cart.service";
import {AllCartDto} from "../../model/all-cart-dto";
import {LineItem} from "../../model/line-item";
import {Order} from "../../model/order";

export const CART_URL = 'cart'

@Component({
  selector: 'app-cart-page',
  templateUrl: './cart-page.component.html',
  styleUrls: ['./cart-page.component.scss']
})
export class CartPageComponent implements OnInit {

  qty?: any;

  content?: AllCartDto;

  id = 0;
  price = 0;
  date = new Date().toDateString();
  status = 'R'


  constructor(public cartService: CartService) {
  }

  ngOnInit(): void {
    this.cartService.findAll().subscribe(
      res => {
        this.content = res;
      }
    )
  }

  delete(id: number) {
    this.cartService.delete(id).subscribe(() => {
      this.ngOnInit();
    });
  }

  deleteQty(id: number, qty: number) {
    this.cartService.deleteQty(id, qty).subscribe(() => {
      this.ngOnInit();
    });
  }

  clearCart(): void {
    this.cartService.clearCart().subscribe(() => {
      this.ngOnInit();
    });
  }

  saveOrder() : void {

    this.cartService.saveOrder(this.id, this.price, this.date, this.status).subscribe(() => {
      this.clearCart();
      this.ngOnInit();
    });
  }

  // ngOnInit(): void {
  //   this.cartUpdated();
  // }
  //
  // cartUpdated() {
  //   this.cartService.findAll().subscribe(
  //     res => {
  //       this.content = res;
  //     }
  //   )
  // }
}

