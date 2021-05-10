

import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { OrderItemInterface } from '../interfaces/order-item.interface';
import { BasketService } from '../services/basket.service';

@Component({
    selector: 'app-account',
    templateUrl: './account.component.html',
    styleUrls: ['./account.component.scss']
})
export class AccountComponent implements OnInit {


    authIsSeller = false

    constructor(
        private basketService : BasketService,
        public authService : AuthService) {
        
    }

    username: string = ""

    ngOnInit(): void { 

        

        this.authService.getAuthUser().subscribe(res => {

            this.username = res.username
            res.authorities.forEach((el:any) => {
                if (el.authority.includes("SELLER")){
                    this.authIsSeller = true
                }
            });
        })
    }

    currentPage : String = "user-orders"
    


    changePage(page: String): void {
        this.currentPage = page
    }

}
