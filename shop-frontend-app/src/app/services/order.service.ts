import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Order} from "../model/order";

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(private http: HttpClient) {
  }

  public findOrdersByUser() {
    return this.http.get<Order[]>('/api/v1/order/all');
  }

  public delete(order: Order){
    return this.http.delete('api/v1/order', ({
      body: order
    }));
  }
}
