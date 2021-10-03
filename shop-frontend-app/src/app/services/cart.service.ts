import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {AddLineItemDto} from "../model/add-line-item-dto";
import {Observable} from "rxjs";
import {AllCartDto} from "../model/all-cart-dto";
import {LineItem} from "../model/line-item";
import {Order} from "../model/order";


@Injectable({
  providedIn: 'root'
})
export class CartService {

  constructor(private http: HttpClient) {
  }

  order: Order | undefined;

  public findAll(): Observable<AllCartDto> {
    return this.http.get<AllCartDto>('/api/v1/cart/all');
  }

  public addToCart(dto: AddLineItemDto) {
    return this.http.post('/api/v1/cart', dto);
  }

  // public removeLineItem(lineItem: LineItem) {
  //   return this.http.delete('api/v1/cart', ({
  //     body: lineItem
  //   }));
  // }


  public delete(id: number) {
    return this.http.delete<LineItem>(`/api/v1/cart/${id}`);
  }

  public deleteQty(id: number, qty: number) {
    return this.http.post(`/api/v1/cart/${id}`, qty);
  }

  public clearCart() {
    return this.http.delete('/api/v1/cart/clear_cart');
  }

  public saveOrder(id: number, price: number, date: string, status: string) {
    let order = new Order(id, date, price, status);
    return this.http.post('/api/v1/order', order);
  }
}
