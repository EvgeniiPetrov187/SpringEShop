import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {CartService} from "../../services/cart.service";
import {AllCartDto} from "../../model/all-cart-dto";

export const CART_URL = 'cart'

@Component({
  selector: 'app-cart-page',
  templateUrl: './cart-page.component.html',
  styleUrls: ['./cart-page.component.scss']
})
export class CartPageComponent implements OnInit {

  content?: AllCartDto;

  id = 0;
  price = 0;
  date = new Date().toDateString();
  status = 'R'

  @Output() updated = new EventEmitter();


  constructor(public cartService: CartService) {
  }

  clearCart(): void {
    this.cartService.clearCart().subscribe(res => {
      this.cartUpdated();
      this.updated.emit();
    });
  }

  saveOrder() : void {
    this.cartService.saveOrder(this.id, this.price, this.date, this.status).subscribe( res => {
      this.clearCart();
    });
  }

  ngOnInit(): void {
    this.cartUpdated();
  }

  cartUpdated() {
    this.cartService.findAll().subscribe(
      res => {
        this.content = res;
      }
    )
  }
}

