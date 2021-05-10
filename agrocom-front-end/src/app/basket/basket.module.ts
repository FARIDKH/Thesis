

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BasketComponent } from './basket.component';
import { BasketService } from '../services/basket.service';

@NgModule({
    declarations: [BasketComponent],
    imports: [ CommonModule ],
    exports: [BasketComponent],
    providers: [BasketService],
})
export class BasketModule {}