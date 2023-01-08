import {redirect} from "@sveltejs/kit";
import type {PageServerLoadEvent} from "./$types";

export const load = async ({ params }: PageServerLoadEvent) => {
    throw redirect(300, `/${params.repository}/tag/main`);
};
