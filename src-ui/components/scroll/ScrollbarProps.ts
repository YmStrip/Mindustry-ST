export type ScrollbarProps = {
	position: 'x' | 'y'
	colorFill?: any
	colorThumb?: any
	//thumb size 0~1
	thumb: number
	//scroll pos 0~1
	scroll: number
	//scroll size
	size?: number
	//padding size
	padding?: number
	//height size
	height: number
	thumbTick?: () => any
}