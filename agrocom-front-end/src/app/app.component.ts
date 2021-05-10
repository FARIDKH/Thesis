import { Component } from '@angular/core';
import { AuthService } from './auth/auth.service';
import { BasketService } from './services/basket.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  
  constructor(
    public basketService: BasketService,
    public authService: AuthService) {}


  


}
