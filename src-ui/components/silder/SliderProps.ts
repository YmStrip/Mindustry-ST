import {ObserverProps} from "@/components/observer/ObserverProps.ts";
import {ObserverFormProps} from "@/components/observer/ObserverFormProps.ts";

export type SliderProps = ObserverProps & ObserverFormProps & {
	padding?: string
	barPadding?: number
	barSize?: number
	min?: number
	max?: number
}