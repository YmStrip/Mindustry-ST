export type PopoverProps = {
	blur?: boolean
	trigger?: 'hover' | 'active' | 'focus' | 'click' | 'switch' | 'manually'
	placement?:
		'left' | 'left-start' | 'left-end' |
		'right' | 'right-start' | 'right-end' |
		'top' | 'top-start' | 'top-end' |
		'bottom' | 'bottom-start' | 'bottom-end'
	to?: string
	easeIn?: number
	easeOut?: number
	zIndex?: number
	style?: any
	class?: number
}