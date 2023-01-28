<script>
    import {onMount} from "svelte";

    export let data;

    let element;
    let loading = true;

    onMount(async () => {
        const {editor} = await import('monaco-editor');

        editor.create(element, {
            value: await (await fetch(`${data.publicApi}/${data.repository.id}/fileversion/${data.fileVersion.id}/preview`)).text(),
            theme: 'vs-dark',
            readOnly: true,
            padding: {
                bottom: '5px',
                top: '5px'
            }
        });

        loading = false;
    });
</script>

<div class="body">
    {#if loading}
        <div class="loading">
            Loading preview...
        </div>
    {/if}
    <div bind:this={element} class="editor"/>
</div>

<style>
    .body {
        height: 500px;
    }

    .loading{
        margin: .5em;
    }

    .editor {
        height: 100%;
    }
</style>
