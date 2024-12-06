<template>
	<div style="width: 100%;height: 100%;pointer-events: none;display: flex;flex-direction: column;">
		<slot name="top"></slot>
		<div style="display: flex;width: 100%;flex: 1;">
			<slot name="left"></slot>
			<slot/>
			<slot name="right"></slot>
		</div>
		<slot name="bottom"></slot>
	</div>
</template>
<script setup>
</script>