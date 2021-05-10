import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AccountComponent } from './account/account.component';
import { AdminComponent } from './admin/admin.component';
import { AuthGuard } from './auth.guard';
import { BasketComponent } from './basket/basket.component';
import { HomeComponent } from './home/home.component';
import { ProductInfoComponent } from './product-info/product-info.component';
import { ShopComponent } from './shop/shop.component';

const routes: Routes = [
  {
    path : "",
    component : ShopComponent
  },
  {
    path: "product/:id",
    component : ProductInfoComponent
  },
  {
    path: "basket",
    component : BasketComponent,
    canActivate : [AuthGuard]
  },
  {
    path : "auth",
    component : HomeComponent,
  },
  {
    path: "admin",
    component: AdminComponent,
    canActivate : [AuthGuard]
  },
  {
    path: "account",
    component: AccountComponent,
    canActivate : [AuthGuard]
  }
  
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
