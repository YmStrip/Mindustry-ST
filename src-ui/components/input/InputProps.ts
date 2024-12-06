import {ObserverFormProps} from "@/components/observer/ObserverFormProps.ts";

export type InputProps = ObserverFormProps & {
	autoFocus?: boolean
	autoSelect?: boolean
	autoSelectMounted?: boolean
	//
	padding?: string
	textAlign?: 'left' | 'right' | 'center'
}