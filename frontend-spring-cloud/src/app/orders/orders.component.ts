import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrl: './orders.component.css'
})
export class OrdersComponent implements  OnInit{
  customerId!:number;
  orders : any;
  constructor(private http:HttpClient, private router: Router , private route: ActivatedRoute) {
    this.customerId=route.snapshot.params['customerID'];
  }
  ngOnInit(): void {
    this.getOrders();
  }

  getOrders(): void {
    this.http.get(`http://localhost:8083/bills/customer/${this.customerId}`)
      .subscribe({
        next: (data) => {
          this.orders = data;
        },
        error: (error) => {
          console.error("Error fetching orders:", error);
        }
      });
  }
}
