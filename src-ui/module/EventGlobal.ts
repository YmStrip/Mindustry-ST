import {EventEmitter} from "@/entity/EventEmitter.ts";

export const EventGlobal = new EventEmitter<{
	'ui:resize': []
	'ui:reload': []
}>()