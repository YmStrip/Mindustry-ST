import {ObserverFormProps} from "@/components/observer/ObserverFormProps.ts";

export type LayoutShrinkBarProps = ObserverFormProps & {
	position: 'left' | 'right' | 'top' | 'bottom'
	size?: number
	sizeMax?: number
	styleBar?: any
	styleExpand?: any
	styleTrigger?: any
	styleIcon?: any
	iconPadding?: number

}