import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import path from "node:path";
import {ProductsComponent} from "./products/products.component";
import {CustomersComponent} from "./customers/customers.component";
import {OrdersComponent} from "./orders/orders.component";

const routes: Routes = [
  {path : "products", component : ProductsComponent},
  {path : "customers", component : CustomersComponent},
  {path : "orders/:customerID" , component : OrdersComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
