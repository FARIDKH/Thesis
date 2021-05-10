import { ProductCategoryInterface } from "./productCategory.interface";
import { UserInterface } from "./user.interface";

export interface ProductInterface {
    id? : Number
    title : String
    description : String
    stock : Number
    constant : String
    productCategoryId? : Number
    productCategory : ProductCategoryInterface
    imgURL? : any
    price? : number
    seller? : UserInterface
    image:File
}


// export class Product implements ProductInterface {
//     id? : Number
//     title: String;
//     description: String;
//     stock: Number;
//     constant: String;
//     productCategoryId?: Number;
//     productCategory! : ProductCategoryInterface
//     price? : number;
//     image:File
    
//     constructor(title: String, description : String, stock: Number, constant: String,image:File){
//         this.title  = title;
//         this.description = description;
//         this.stock = stock;
//         this.constant = constant
//         this.image = image
//     }

// }