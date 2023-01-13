<script>
    import {diffIds} from "./history/stores.js";
    import {fade} from "svelte/transition";

    export let data;
</script>

<div class="body">
    <div class="header">
        <h3>{data.fileVersion.fileName}</h3>

        {#if $diffIds.length > 1}
            <a class="diff" href="/{data.repository.name}/diff/{$diffIds[0]}/{$diffIds[1]}" in:fade>view diff</a>
        {/if}
    </div>

    <div class="content">
        <slot/>
    </div>
</div>

<style>
    .header {
        margin-top: 1em;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .diff {
        color: #c81ae1;
    }

    a, a:visited {
        text-decoration: none;
    }
</style>
