<script>
    import { goto, } from '$app/navigation';
    export let data;

    function changeTagName() {
        goto(`/${data.repository.name}/tag/${data.tagName}`);
    }
</script>

<div class="body">
    <div class="header">
        <select bind:value={data.tagName} on:change={changeTagName}>
            {#each data.repository.tagNames as tagName (tagName)}
                <option>{tagName}</option>
            {/each}
        </select>
    </div>

    <div class="content">
        <slot/>
    </div>
</div>

<style>
    .header {
        margin-top: 1em;
    }

    select {
        border: none;
        border-radius: 8px;
        padding: .75em;
        background-color: #383838;
        font-size: .9em;
        cursor: pointer;
        display: inline-block;
    }

    .content {
        margin-top: 2em;
    }
</style>
