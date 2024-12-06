import {IDProvider} from "@/module/id/entity/IDProvider.ts";

export interface IDP<T = any> {
	idp: IDProvider<T>
}