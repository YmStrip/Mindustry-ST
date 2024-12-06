import {ObserverFormProps} from "@/components/observer/ObserverFormProps.ts";
import {SelectOption} from "@/components/select/SelectOption.ts";
import {PopoverProps} from "@/components/popover/PopoverProps.ts";

export type SelectProps = ObserverFormProps & {
	search?: boolean
	options?: SelectOption[]
	filter?: ((value: any) => boolean)
	defaultValue?: any
	popover?:PopoverProps
	popoverStyle?:any
}