import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AccountComponent } from './account.component';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { HttpInterceptorService } from '../helpers/httpinterceptor.service';
import { ProductModule } from './products/product.module';
import { UserOrdersModule } from './user-orders/user-order.module';

@NgModule({
    declarations: [AccountComponent],
    imports: [ CommonModule, ProductModule,UserOrdersModule ],
    exports: [AccountComponent],
    providers: [
        // { provide: HTTP_INTERCEPTORS, useClass: HttpInterceptorService, multi: true }
    ],
})
export class AccountModule {}