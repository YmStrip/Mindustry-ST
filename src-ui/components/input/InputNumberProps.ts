import {InputProps} from "@/components/input/InputProps.ts";

export type InputNumberProps = InputProps & {
	min?: number
	max?: number
	precision?: number
	step?:number
}