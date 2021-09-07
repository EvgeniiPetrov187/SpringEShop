import {Category} from "./category";
import {Brand} from "./brand";

export class Product {

  constructor(public id: number,
              public name: string,
              public description: string,
              public cost: number,
              public category: Category,
              public brand: Brand,
              public pictures: number[]) {
  }
}
