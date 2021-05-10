import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "src/environments/environment";


@Injectable()
export class RateService{

    constructor(private httpClient: HttpClient){}

    rateSeller(raterId:number,sellerId:number,rateValue:number){
        console.log({
            "raterId" : raterId,
            "sellerId" : sellerId,
            "value" : rateValue
        })
        return this.httpClient.post(
            environment.host + "/rate",
            {
                "raterId" : raterId,
                "sellerId" : sellerId,
                "value" : rateValue
            }
        )
    }

    getRate(sellerId:number) {
        return this.httpClient.get<Number>(
            environment.host + "/rate/getSellerRating/" + sellerId
        )
    }


}