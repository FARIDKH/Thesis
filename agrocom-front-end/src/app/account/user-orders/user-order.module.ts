

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserOrdersComponent } from './user-order.component';
import { AccountModule } from '../account.module';

@NgModule({
    declarations: [UserOrdersComponent],
    imports: [ CommonModule ],
    exports: [UserOrdersComponent],
    providers: [],
})
export class UserOrdersModule  {}