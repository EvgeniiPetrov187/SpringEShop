import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {LineItem} from "../../model/line-item";
import {CartService} from "../../services/cart.service";

@Component({
  selector: '[app-cart-item]',
  templateUrl: './cart-item.component.html',
  styleUrls: ['./cart-item.component.scss']
})
export class CartItemComponent implements OnInit {

  @Output() updated = new EventEmitter();

  private _lineItem?: LineItem;

  qty: number = 0;

  constructor(private cartService: CartService) {
  }

  ngOnInit(): void {
  }

  @Input()
  set lineItem(value: LineItem | undefined) {
    this._lineItem = value;
    this.qty = value ? value.qty : 0;
  }

  get lineItem(): LineItem | undefined {
    return this._lineItem;
  }

  // delete(id: number) {
  //   this.cartService.delete(id).subscribe(() => {
  //     this.ngOnInit();
  //   });
  // }
  //
  // deleteQty(id: number, qty: number) {
  //   this.cartService.deleteQty(id, qty).subscribe(() => {
  //     this.ngOnInit();
  //   });
  // }
}
