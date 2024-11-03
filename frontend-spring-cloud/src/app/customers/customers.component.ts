import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {error} from "@angular/compiler-cli/src/transformers/util";
import {Route, Router} from "@angular/router";

@Component({
  selector: 'app-customers',
  templateUrl: './customers.component.html',
  styleUrl: './customers.component.css'
})
export class CustomersComponent implements OnInit{

   customers : any;
  constructor(private http:HttpClient, private route: Router) {
  }
  ngOnInit(): void {
    this.http.get("http://localhost:8888/CUSTOMER-SERVICE/customers").subscribe({
      next : (data)=>{
        this.customers=data;
      },
      error : (err)=>{}
    })

  }


  getOrders(p: any) {
    this.route.navigateByUrl("/orders/"+p.id);
  }
}
