import {ObserverProps} from "@/components/observer/ObserverProps.ts";

export type ObserverFormProps = ObserverProps & {
	name?: string
	display?: 'solid' | 'solid-bevel' | 'bevel' | 'arrow' | 'bracket' | 'none'
	height?: number
	color?: string
	dashed?: boolean
	aBorderActive?: number
	aBorderHover?: number
	aBorder?: number
	aBackActive?: number
	aBackHover?: number
	aBack?: number
	//
	borderTop?: boolean
	borderRight?: boolean
	borderBottom?: boolean
	borderLeft?: boolean
	//
	pointLeftTop?: boolean
	pointLeftBottom?: boolean
	pointRightTop?: boolean
	pointRightBottom?: boolean
}