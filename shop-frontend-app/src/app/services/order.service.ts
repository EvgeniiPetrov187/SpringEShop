import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Order} from "../model/order";
import {Observable} from "rxjs";
import {AllCartDto} from "../model/all-cart-dto";

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(private http: HttpClient) {
  }

  public findOrdersByUser(userId: number) {
    return this.http.get<Order[]>(`/api/v1/order/${userId}/user`);
  }
}
