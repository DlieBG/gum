<script>
    import {fly} from 'svelte/transition';

    export let data;
</script>

<div class="body" in:fly={{y: 20}}>
    <div class="header">
        <div>
            Locks
        </div>
    </div>

    <div class="content">
        <slot/>
    </div>
</div>

<style>
    .body {
        border: 1px solid #383838;
        border-radius: 8px;
        margin-top: 1em;
    }

    .header {
        border-radius: 8px 8px 0 0;
        background-color: #383838;
        margin: -1px;
        padding: .5em;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .content {
        padding: .5em;
    }
</style>
