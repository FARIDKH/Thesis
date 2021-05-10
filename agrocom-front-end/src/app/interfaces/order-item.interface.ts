import { OrderInterface } from "./order.interface";
import { ProductInterface } from "./product.interface";
import { UserInterface } from "./user.interface";

export interface OrderItemInterface {
    amount : number
    customer : UserInterface
    id : Number
    order: OrderInterface
    product: ProductInterface
    status : String
}