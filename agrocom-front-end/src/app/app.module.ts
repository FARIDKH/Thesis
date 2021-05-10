import { APP_BASE_HREF } from '@angular/common';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { AccountModule } from './account/account.module';
import { AdminModule } from './admin/admin.module';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuthGuard } from './auth.guard';
import { BasketModule } from './basket/basket.module';
import { HomeModule } from './home/home.module';
import { ProductInfoModule } from './product-info/product-info.module';
import { BasketService } from './services/basket.service';
import { ShopModule } from './shop/shop.module';


@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HomeModule,
    ShopModule,
    AdminModule,
    AccountModule,
    RouterModule,
    ProductInfoModule,
    BasketModule
  ],
  providers: [{provide: APP_BASE_HREF, useValue: '/'}, AuthGuard, BasketService],
  bootstrap: [AppComponent],
})
export class AppModule { }
