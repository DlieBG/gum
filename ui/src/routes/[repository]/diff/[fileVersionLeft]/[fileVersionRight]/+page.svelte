<script>
    import {editor} from 'monaco-editor';
    import {onMount} from "svelte";

    export let data;

    let element;
    let loading = true;

    onMount(async () => {
        let diffEditor = editor.createDiffEditor(element, {
            theme: 'vs-dark',
            readOnly: true,
            padding: {
                bottom: '5px',
                top: '5px'
            }
        });

        diffEditor.setModel({
            original: editor.createModel(
                await (await fetch(`${data.publicApi}/${data.repository.id}/fileversion/${data.diffFileVersions[0].id}/preview`)).text(),
                'text/plain'
            ),
            modified: editor.createModel(
                await (await fetch(`${data.publicApi}/${data.repository.id}/fileversion/${data.diffFileVersions[1].id}/preview`)).text(),
                'text/plain'
            )
        });

        loading = false;
    });
</script>

<div class="body">
    {#if loading}
        <div class="loading">
            Loading diff...
        </div>
    {/if}
    <div bind:this={element} class="editor"/>
</div>

<style>
    .body {
        height: 500px;
    }

    .loading {
        margin: .5em;
    }

    .editor {
        height: 100%;
    }
</style>
