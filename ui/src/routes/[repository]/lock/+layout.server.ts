import {LockService} from "$lib/services/lock.service";
import type {PageServerLoadEvent} from "./$types";

export const load = async ({params}: PageServerLoadEvent) => {
    return {
        locks: await LockService.getLocks(params.repository)
    };
}
