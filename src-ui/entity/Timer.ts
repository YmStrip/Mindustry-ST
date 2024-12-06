//lots of time
export class Timer {
	static setTimout(a: any, b: any = 0) {
		return setTimeout(a, b)
	}
	paused = false
	asyncCallIter = 0
	//call after sleep , but only once in call multi
	callAsync(call: () => any, dt = 250) {
		const v = ++this.asyncCallIter
		setTimeout(() => {
			if (v != this.asyncCallIter) return
			call()
		}, dt)
	}
	asyncCallContinueTrigger = false
	asyncCallContinue = 0
	asyncCallContinueIter = 0
	//within dt, every time the specified number of consecutive calls is made, the last time is triggered, which is applicable to double click.
	//call will happened after last dt,
	callContinue(call: () => any, dt = 250, lt = 2) {
		if (!this.asyncCallContinueTrigger) {
			this.asyncCallContinueTrigger = true
			this.asyncCallContinueIter = 0
			const p = ++this.asyncCallContinue
			setTimeout(() => {
				if (p != this.asyncCallContinue) return
				this.asyncCallContinueTrigger = false
				this.asyncCallContinueIter = 0
			}, dt)
		}
		this.asyncCallContinueIter++
		if (this.asyncCallContinueIter >= lt) {
			this.asyncCallContinueIter = 0
			this.asyncCallContinueTrigger = false
			this.asyncCallContinue++
			call()
		}
	}
	asyncCallInter = 0
	//for any interval = dt, only call once (first)
	callInter(call: () => any, dt = 250) {
		const now = Date.now()
		//[index]...[dt][dt][now]
		//set index = now
		if (this.asyncCallInter < now - dt * 2) {
			this.asyncCallInter = now
			return call()
		}
		//[dt index][dt][now]
		//between last dt and dt1 , so add a dt
		if (this.asyncCallInter < now - dt && this.asyncCallInter >= now - dt * 2) {
			this.asyncCallInter += dt
			return call()
		}
		//[dt][dt index][now] pass
	}
	//wait
	cooldownTime = 0
	cooldown(a: (() => any) | number = 250, b?: number) {
		const cooldown = (dt: number) => {
			this.cooldownTime = Date.now() + dt
		}
		if (typeof a === "number") {
			return cooldown(a)
		}
		if (typeof a === 'function') {
			if (Date.now() > this.cooldownTime) {
				a()
			}
		}
		if (typeof b === 'number') {
			return cooldown(b)
		}
	}
	//timeout
	timerIter: any
	onTimer() {
	}
	//clear() not trigger,and timer(*,*<0) mode never trigger
	onEnd() {
	}
	pause() {
		this.paused = true
	}
	continue() {
		this.paused = false
	}
	count = 0;
	countIter = 0
	nextSecond() {
		const now = Date.now()
		return now + (1000 - (now % 1000))
	}
	nextSecondDt() {
		const now = Date.now()
		return (1000 - (now % 1000))
	}
	//count<0 infinity run
	timer(dt = 250, count: number = 1) {
		this.clear()
		const it = ++this.countIter
		count = Math.round(count)
		if (!Number.isInteger(count) || !Number.isFinite(count) || isNaN(count)) return;
		this.count = count
		if (this.count == 0) return
		this.timerIter = setInterval(() => {
			if (it !== this.countIter) return
			if (this.paused) return;
			//why not return?
			if (it == this.countIter) this.onTimer()
			if (it == this.countIter && this.count == 0) {
				this.clear()
				this.onEnd()
			}
			else if (it == this.countIter && this.count > 0) {
				this.count--
			}
		}, dt)
	}
	setTimeout(fn: any, dt = 250) {
		this.onTimer = fn
		this.timer(dt)
		return this
	}
	setInterval(fn: any, dt = 250) {
		this.onTimer = fn
		this.timer(dt, -1)
		return this
	}
	clear() {
		++this.countIter
		clearInterval(this.timerIter)
		this.timerIter = null
		this.count = 0
		this.paused = false
	}
	wait(dt = 0) {
		return new Promise(t => setTimeout(t, dt))
	}
}