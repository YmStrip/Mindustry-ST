//@ts-nocheck
import ColorHash from 'color-hash'


export type ColorMixType =
	'min'
	| 'max'
	| 'mul'
	| 'screen'
	| 'burn'
	| 'dodge'
	| 'linearBurn'
	| 'linearDodge'
	| 'overlay'
	| 'hardLight'
	| 'softLight'
	| 'vividLight'
	| 'pinLight'
	| 'linearLight'
	| 'hardMix'
	| 'difference'
	| 'mix'
	| 'exclusion'
export class Color {
	static mixType = [
		'min'
		, 'max'
		, 'mul'
		, 'screen'
		, 'burn'
		, 'dodge'
		, 'linearBurn'
		, 'linearDodge'
		, 'overlay'
		, 'hardLight'
		, 'softLight'
		, 'vividLight'
		, 'pinLight'
		, 'linearLight'
		, 'hardMix'
		, 'difference'
		, 'mix'
		, 'exclusion'
	]
	static colorList = {
		indianred: '#cd5c5c',
		lightcoral: '#f08080',
		salmon: '#fa8072',
		darksalmon: '#e9967a',
		lightsalmon: '#ffa07a',
		crimson: '#dc143c',
		red: '#ff0000',
		darkred: '#8b0000',
		pink: '#ffc0cb',
		lightpink: '#ffb6c1',
		hotpink: '#ff69b4',
		deeppink: '#ff1493',
		mediumvioletred: '#c71585',
		palevioletred: '#db7093',
		coral: '#ff7f50',
		tomato: '#ff6347',
		orangered: '#ff4500',
		darkorange: '#ff8c00',
		orange: '#ffa500',
		gold: '#ffd700',
		yellow: '#ffff00',
		lightyellow: '#ffffe0',
		lemonchiffon: '#fffacd',
		lightgoldenrodyellow: '#fafad2',
		papayawhip: '#ffefd5',
		moccasin: '#ffe4b5',
		peachpuff: '#ffdab9',
		palegoldenrod: '#eee8aa',
		khaki: '#f0e68c',
		darkkhaki: '#bdb76b',
		lavender: '#e6e6fa',
		thistle: '#d8bfd8',
		plum: '#dda0dd',
		violet: '#ee82ee',
		orchid: '#da70d6',
		fuchsia: '#ff00ff',
		magenta: '#ff00ff',
		mediumorchid: '#ba55d3',
		mediumpurple: '#9370db',
		rebeccapurple: '#663399',
		blueviolet: '#8a2be2',
		darkviolet: '#9400d3',
		darkorchid: '#9932cc',
		darkmagenta: '#8b008b',
		purple: '#800080',
		indigo: '#4b0082',
		slateblue: '#6a5acd',
		darkslateblue: '#483d8b',
		mediumslateblue: '#7b68ee',
		greenyellow: '#adff2f',
		chartreuse: '#7fff00',
		lawngreen: '#7cfc00',
		lime: '#00ff00',
		limegreen: '#32cd32',
		palegreen: '#98fb98',
		lightgreen: '#90ee90',
		mediumspringgreen: '#00fa9a',
		springgreen: '#00ff7f',
		mediumseagreen: '#3cb371',
		seagreen: '#2e8b57',
		forestgreen: '#228b22',
		green: '#008000',
		darkgreen: '#006400',
		yellowgreen: '#9acd32',
		olivedrab: '#6b8e23',
		olive: '#6b8e23',
		darkolivegreen: '#556b2f',
		mediumaquamarine: '#66cdaa',
		darkseagreen: '#8fbc8b',
		lightseagreen: '#20b2aa',
		darkcyan: '#008b8b',
		teal: '#008080',
		aqua: '#00ffff',
		cyan: '#00ffff',
		lightcyan: '#e0ffff',
		paleturquoise: '#afeeee',
		aquamarine: '#7fffd4',
		turquoise: '#40e0d0',
		mediumturquoise: '#48d1cc',
		darkturquoise: '#00ced1',
		cadetblue: '#5f9ea0',
		steelblue: '#4682b4',
		lightsteelblue: '#b0c4de',
		powderblue: '#b0e0e6',
		lightblue: '#add8e6',
		skyblue: '#87ceeb',
		lightskyblue: '#87cefa',
		deepskyblue: '#00bfff',
		dodgerblue: '#1e90ff',
		cornflowerblue: '#6495ed',
		royalblue: '#4169e1',
		blue: '#0000ff',
		mediumblue: '#0000cd',
		darkblue: '#00008b',
		navy: '#00008b',
		midnightblue: '#191970',
		cornsilk: '#fff8dc',
		blanchedalmond: '#ffebcd',
		bisque: '#ffe4c4',
		navajowhite: '#ffdead',
		wheat: '#f5deb3',
		burlywood: '#deb887',
		tan: '#d2b48c',
		rosybrown: '#bc8f8f',
		sandybrown: '#f4a460',
		goldenrod: '#daa520',
		darkgoldenrod: '#b8860b',
		peru: '#cd853f',
		chocolate: '#d2691e',
		saddlebrown: '#8b4513',
		sienna: '#a0522d',
		brown: '#a52a2a',
		maroon: '#800000',
		white: '#ffffff',
		snow: '#fffafa',
		honeydew: '#f0fff0',
		mintcream: '#f5fffa',
		azure: '#f0ffff',
		aliceblue: '#f0f8ff',
		ghostwhite: '#f8f8ff',
		whitesmoke: '#f5f5f5',
		seashell: '#fff5ee',
		beige: '#f5f5dc',
		oldlace: '#fdf5e6',
		floralwhite: '#fdf5e6',
		ivory: '#fffff0',
		antiquewhite: '#faebd7',
		linen: '#faf0e6',
		lavenderblush: '#fff0f5',
		mistyrose: '#ffe4e1',
		gainsboro: '#dcdcdc',
		lightgray: '#d3d3d3',
		silver: '#c0c0c0',
		darkgray: '#a9a9a9',
		gray: '#808080',
		dimgray: '#696969',
		lightslategray: '#778899',
		slategray: '#708090',
		darkslategray: '#2f4f4f',
		black: '#000000'
	}
	static map(x: number, a: number, b: number, c: number, d: number) {
		const norm = (x - a) / (b - a)
		return c + norm * d
	}
	//calc image filter string
	static filterScreen(src: any, target: any) {
		const a = new Color(src)
		const b = new Color(target)
		const ha = a.hsl()
		const hb = b.hsl()
		let h = hb[0] - ha[0],
			s = hb[1] / ha[1],
			l = hb[2] / ha[2]
		s = s / hb[2]

		if (hb[2] == 0) {
			l = 0
			s = 0
		}
		else {
			l = l * (hb[1] < .5 ? this.map(hb[1], 0, 1, 2, 1) : this.map(hb[1], 0, 1, 1, 0))
		}
		return {
			h: h,
			s: s,
			l: l,
			toString() {
				return `hue-rotate(${h * 360}deg) saturate(${s}) brightness(${l}) opacity(${b.a})`
			}
		}
	}
	static rgbToHsl([r, g, b]: number[]): number[] {
		const max = Math.max(r, g, b);
		const min = Math.min(r, g, b);
		let h = 0, s = 0, l = (max + min) / 2;
		if (max !== min) {
			const d = max - min;
			s = l > 0.5 ? d / (2 - max - min) : d / (max + min);
			switch (max) {
				case r:
					h = (g - b) / d + (g < b ? 6 : 0);
					break;
				case g:
					h = (b - r) / d + 2;
					break;
				case b:
					h = (r - g) / d + 4;
					break;
			}
			h /= 6;
		}
		return [h, s, l];
	}
	static hslToRgb([h, s, l]: number[]): number[] {
		let r: number, g: number, b: number;
		if (s === 0) {
			r = g = b = l; // achromatic
		}
		else {
			const hue2rgb = (p: number, q: number, t: number): number => {
				if (t < 0) t += 1;
				if (t > 1) t -= 1;
				if (t < 1 / 6) return p + (q - p) * 6 * t;
				if (t < 1 / 2) return q;
				if (t < 2 / 3) return p + (q - p) * (2 / 3 - t) * 6;
				return p;
			};
			const q = l < 0.5 ? l * (1 + s) : l + s - l * s;
			const p = 2 * l - q;
			r = hue2rgb(p, q, h + 1 / 3);
			g = hue2rgb(p, q, h);
			b = hue2rgb(p, q, h - 1 / 3);
		}
		return [r, g, b];
	}
	static rgbToHsv([r, g, b]: number[]): number[] {
		const max = Math.max(r, g, b);
		const min = Math.min(r, g, b);
		const d = max - min;
		const s = max === 0 ? 0 : d / max;
		let h = 0;
		const v = max;
		if (max !== min) {
			switch (max) {
				case r:
					h = (g - b) / d + (g < b ? 6 : 0);
					break;
				case g:
					h = (b - r) / d + 2;
					break;
				case b:
					h = (r - g) / d + 4;
					break;
			}
			h /= 6;
		}
		return [h, s, v];
	}
	static hsvToRgb([h, s, v]: number[]): number[] {
		let r: number = 0, g: number = 0, b: number = 0;
		const i = Math.floor(h * 6);
		const f = h * 6 - i;
		const p = v * (1 - s);
		const q = v * (1 - f * s);
		const t = v * (1 - (1 - f) * s);
		switch (i % 6) {
			case 0:
				r = v;
				g = t;
				b = p;
				break;
			case 1:
				r = q;
				g = v;
				b = p;
				break;
			case 2:
				r = p;
				g = v;
				b = t;
				break;
			case 3:
				r = p;
				g = q;
				b = v;
				break;
			case 4:
				r = t;
				g = p;
				b = v;
				break;
			case 5:
				r = v;
				g = p;
				b = q;
				break;
		}
		return [r, g, b];
	}
	static fac(a: number, b: number, res: number, fac: number) {
		if (fac <= .5) {
			//A=-2x+1,C=2kx
			return (-2 * fac + 1) * a + (2 * fac) * res
		}
		else {
			//B=2x-1,C=-2x+2
			return (2 * fac - 1) * b + (-2 * fac + 2) * res
		}
	}
	static colorCalc(a: number, b: number, fac: number, call: (a: number, b: number) => number) {
		return this.fac(a, b, Math.max(Math.min(call(a, b), 1), 0), fac)
	}
	//A,[x,y,w,h],B[x,y,w,h] => a:[x,y,x1,y1]
	static crossArea(a: number[], b: number[]): undefined | (number[][]) {
		const x1A = a[0];
		const y1A = a[1];
		const x2A = a[0] + a[2];
		const y2A = a[1] + a[3];
		const x1B = b[0];
		const y1B = b[1];
		const x2B = b[0] + b[2];
		const y2B = b[1] + b[3];
		const x_overlap_start = Math.max(x1A, x1B);
		const x_overlap_end = Math.min(x2A, x2B);
		const y_overlap_start = Math.max(y1A, y1B);
		const y_overlap_end = Math.min(y2A, y2B);
		if (x_overlap_start >= x_overlap_end || y_overlap_start >= y_overlap_end) {
			return undefined
		}
		const overlapA = [
			x_overlap_start - x1A,
			y_overlap_start - y1A,
			x_overlap_end - x1A,
			y_overlap_end - y1A,
		];
		const overlapB = [
			x_overlap_start - x1B,
			y_overlap_start - y1B,
			x_overlap_end - x1B,
			y_overlap_end - y1B,
		];
		return [overlapA, overlapB]
	}
	static mix(a: number, b: number, fac = .5) {
		return this.colorCalc(a, b, fac, (a, b) => {
			return (a + b) / 2
		})
	}
	static min(a: number, b: number, fac = .5) {
		return this.colorCalc(a, b, fac, (a, b) => {
			return Math.min(a, b)
		})
	}
	static max(a: number, b: number, fac = .5) {
		return this.colorCalc(a, b, fac, (a, b) => {
			return Math.max(a, b)
		})
	}
	static mul(a: number, b: number, fac = .5) {
		return this.colorCalc(a, b, fac, (a, b) => {
			return a * b
		})
	}
	static screen(a: number, b: number, fac = .5) {
		return this.colorCalc(a, b, fac, (a, b) => {
			return 1 - (1 - a) * (1 - b)
		})
	}
	static burn(a: number, b: number, fac = .5) {
		return this.colorCalc(a, b, fac, (a, b) => {
			return a - (1 - a) * (1 - b) / b
		})
	}
	static dodge(a: number, b: number, fac = .5) {
		return this.colorCalc(a, b, fac, (a, b) => {
			return a + (a * b) / (1 - b)
		})
	}
	static linearBurn(a: number, b: number, fac = .5) {
		return this.colorCalc(a, b, fac, (a, b) => {
			return a + b - 1
		})
	}
	static linearDodge(a: number, b: number, fac = .5) {
		return this.colorCalc(a, b, fac, (a, b) => {
			return a + b
		})
	}
	static overlap(a: number, b: number, fac = .5) {
		return this.colorCalc(a, b, fac, (a, b) => {
			if (a <= .5) {
				return a * b / 2
			}
			else {
				return 1 - (1 - a) * (1 - b) / 2
			}
		})
	}
	static hardLight(a: number, b: number, fac = .5) {
		return this.colorCalc(a, b, fac, (a, b) => {
			if (b <= .5) {
				return a * b / 2
			}
			else {
				return 1 - (1 - a) * (1 - b) / 2
			}
		})
	}
	static softLight(a: number, b: number, fac = .5) {
		return this.colorCalc(a, b, fac, (a, b) => {
			if (b <= .5) {
				return a * b / 2 + a * a * (1 - 2 * b)
			}
			else {
				return a * (1 - b) / 2 + Math.sqrt(a) * (2 * b - 1)
			}
		})
	}
	static vividLight(a: number, b: number, fac = .5) {
		return this.colorCalc(a, b, fac, (a, b) => {
			if (b <= .5) {
				return a - (1 - a) * (1 - 2 * b) / (2 * b)
			}
			else {
				return a + a * (2 * b - 1) / (2 * (1 - b))
			}
		})
	}
	static pinLight(a: number, b: number, fac = .5) {
		return this.colorCalc(a, b, fac, (a, b) => {
			if (b <= .5) {
				return Math.min(a, 2 * b)
			}
			else {
				return Math.min(a, 2 * b - 1)
			}
		})
	}
	static linearLight(a: number, b: number, fac = .5) {
		return this.colorCalc(a, b, fac, (a, b) => {
			return a + 2 * b - 1
		})
	}
	static hardMix(a: number, b: number, fac = .5) {
		return this.colorCalc(a, b, fac, (a, b) => {
			return a + b > 1 ? 1 : 0
		})
	}
	static exclusion(a: number, b: number, fac = .5) {
		return this.colorCalc(a, b, fac, (a, b) => {
			return a + b - a * b / 2
		})
	}
	static difference(a: number, b: number, fac = .5) {
		return this.colorCalc(a, b, fac, (a, b) => {
			return Math.abs(a - b)
		})
	}
	//
	//0~1
	public r = 1
	//0~1
	public g = 1
	//0~1
	public b = 1
	//0~1
	public a = 1
	alpha(a: number) {
		this.a = this.clampNumber(a)
		return this
	}
	mulAlpha(a:number) {
		this.a = this.clampNumber(this.a*a)
		return this
	}
	addAlpha(a:number) {
		this.a = this.clampNumber(this.a+a)
		return this
	}
	hex() {
		return "#" +
			Math.round(this.r * 255).toString(16).padStart(2, '0') +
			Math.round(this.g * 255).toString(16).padStart(2, '0') +
			Math.round(this.b * 255).toString(16).padStart(2, '0') +
			Math.round(this.a * 255).toString(16).padStart(2, '0');
	}
	rgba() {
		return `rgba(${Math.round(this.r * 255)},${Math.round(this.g * 255)},${Math.round(this.b * 255)},${this.a})`
	}
	hsl() {
		return Color.rgbToHsl([this.r, this.g, this.b])
	}
	hsv() {
		return Color.rgbToHsv([this.r, this.g, this.b])
	}
	calc(color: Color, fac = .5, call: (a: number, b: number, fac: number) => number) {
		const copy = new Color(this)
		copy.r = call(copy.r, color.r, fac)
		copy.g = call(copy.g, color.g, fac)
		copy.b = call(copy.b, color.b, fac)
		copy.a = (color.a + copy.a) / 2
		return copy.clamp()
	}
	min(color: Color, fac = .5) {
		return this.calc(color, fac, (a, b, c) => Color.min(a, b, c))
	}
	max(color: Color, fac = .5) {
		return this.calc(color, fac, (a, b, c) => Color.max(a, b, c))
	}
	mul(color: Color, fac = .5) {
		return this.calc(color, fac, (a, b, c) => Color.mul(a, b, c))
	}
	screen(color: Color, fac = .5) {
		return this.calc(color, fac, (a, b, c) => Color.screen(a, b, c))
	}
	burn(color: Color, fac = .5) {
		return this.calc(color, fac, (a, b, c) => Color.burn(a, b, c))
	}
	dodge(color: Color, fac = .5) {
		return this.calc(color, fac, (a, b, c) => Color.dodge(a, b, c))
	}
	linearBurn(color: Color, fac = .5) {
		return this.calc(color, fac, (a, b, c) => Color.linearBurn(a, b, c))
	}
	linearDodge(color: Color, fac = .5) {
		return this.calc(color, fac, (a, b, c) => Color.linearDodge(a, b, c))
	}
	overlay(color: Color, fac = .5) {
		return this.calc(color, fac, (a, b, c) => Color.overlap(a, b, c))
	}
	hardLight(color: Color, fac = .5) {
		return this.calc(color, fac, (a, b, c) => Color.hardLight(a, b, c))
	}
	softLight(color: Color, fac = .5) {
		return this.calc(color, fac, (a, b, c) => Color.softLight(a, b, c))
	}
	vividLight(color: Color, fac = .5) {
		return this.calc(color, fac, (a, b, c) => Color.vividLight(a, b, c))
	}
	pinLight(color: Color, fac = .5) {
		return this.calc(color, fac, (a, b, c) => Color.pinLight(a, b, c))
	}
	linearLight(color: Color, fac = .5) {
		return this.calc(color, fac, (a, b, c) => Color.linearLight(a, b, c))
	}
	hardMix(color: Color, fac = .5) {
		return this.calc(color, fac, (a, b, c) => Color.hardLight(a, b, c))
	}
	difference(color: Color, fac = .5) {
		return this.calc(color, fac, (a, b, c) => Color.difference(a, b, c))
	}
	mix(color: Color, fac = .5) {
		return this.calc(color, fac, (a, b, c) => Color.mix(a, b, c))
	}
	exclusion(color: Color, fac = .5) {
		return this.calc(color, fac, (a, b, c) => Color.exclusion(a, b, c))
	}
	darkener(val: number) {
		const hsl = this.hsl()
		hsl[2] = hsl[2] / Math.pow(2, val)
		return this.fromHsl(hsl)
	}
	lightener(val: number) {
		return this.darkener(-1 * val)
	}
	fromHsl(hsl: number[]) {
		const [r, g, b] = Color.hslToRgb(hsl)
		this.r = r
		this.g = g
		this.b = b
		return this.clamp()
	}
	fromHsv(hsv: number[]) {
		const [r, g, b] = Color.hsvToRgb(hsv)
		this.r = r
		this.g = g
		this.b = b
		return this.clamp()
	}
	fromRgba([r, g, b, a]: number[]) {
		this.r = r || this.r
		this.g = g || this.g
		this.b = b || this.b
		this.a = a || this.a
		return this.clamp()
	}
	fromRgbaStr(rgba: string) {
		const trimmedInput = rgba.replace(/^(rgb|rgba)\s*\(|\s*\)$/, '');
		const components = trimmedInput.split(/\s*,\s*/
		).map((component, index) => {
			if (index === 3 && component === '') {
			}
			return parseFloat(component);
		});
		if (components.length === 3) {
			components.push(1);
		}
		else if (components.length !== 4) {
			throw new Error('Invalid RGB or RGBA format');
		}
		const red = components[0] / 255;
		const green = components[1] / 255;
		const blue = components[2] / 255;
		const alpha = components[3];
		this.r = red
		this.g = green
		this.b = blue
		this.a = alpha
		return this.clamp()
	}
	fromHex(hex: string) {
		hex = hex.replace('#', '');
		if (hex.length === 3) {
			hex = hex[0] + hex[0] + hex[1] + hex[1] + hex[2] + hex[2];
		}
		else if (hex.length === 4) {
			hex = hex[0] + hex[0] + hex[1] + hex[1] + hex[2] + hex[2] + hex[3] + hex[3];
		}
		else if (hex.length === 6) {
		}
		else if (hex.length === 8) {
		}
		else {
			throw new Error('Invalid hex color format');
		}
		const r = parseInt(hex.slice(0, 2), 16);
		const g = parseInt(hex.slice(2, 4), 16);
		const b = parseInt(hex.slice(4, 6), 16);
		let a = 1;
		if (hex.length === 8) {
			a = parseInt(hex.slice(6, 8), 16) / 255;
		}
		const red = r / 255;
		const green = g / 255;
		const blue = b / 255;
		this.r = red
		this.g = green
		this.b = blue
		this.a = a
		return this.clamp()
	}
	clampNumber(val: number) {
		if (!Number.isFinite(val) || isNaN(val)) return 1
		return Math.min(val, Math.max(val, 0), 1)
	}
	clamp() {
		this.r = this.clampNumber(this.r)
		this.g = this.clampNumber(this.g)
		this.b = this.clampNumber(this.b)
		this.a = this.clampNumber(this.a)
		return this
	}
	from(data: any) {
		if (data instanceof Color) {
			this.r = data.r
			this.g = data.g
			this.b = data.b
			this.a = data.a
			return this.clamp()
		}
		if (Array.isArray(data)) return this.fromRgba(data)
		if (!(typeof data === 'string')) throw new Error('unknown color:' + data)
		data = data.trim().toLowerCase()
		const def = Color.colorList[data]
		if (def) return this.fromHex(def)
		if (data.startsWith('#')) return this.fromHex(data)
		if (data.startsWith('r')) return this.fromRgbaStr(data)
		throw new Error('unknown color:' + data)
	}
	fromSafe(data: any) {
		try {
			this.from(data)
		} catch (e) {
			this.from('white')
		}
	}
	//color hash
	fromHash(color: any, option?: ColorHash.ColorHashOptions) {
		const [r, g, b] = new ColorHash(option).rgb(color + '')
		this.r = r / 255
		this.g = g / 255
		this.b = b / 255
		return this.clamp()
	}
	fromRandom(min = 0, max = 1) {
		this.r = Math.random() * (max - min) + min
		this.g = Math.random() * (max - min) + min
		this.b = Math.random() * (max - min) + min
		return this.clamp()
	}
	toString() {
		return this.hex()
	}
	constructor(color?: string | [number, number, number, number] | Color) {
		if (color) this.fromSafe(color)
	}
}