<script>
    import {fly} from 'svelte/transition';
    import TextPreview from "./TextPreview.svelte";
    import ImagePreview from "./ImagePreview.svelte";
    import PdfPreview from "./PdfPreview.svelte";

    export let data;
</script>

<div class="body" in:fly={{y: 20}}>
    <div class="header">
        <div class="info">
            <img src={data.fileVersion.avatar}>

            <div>
                FileVersion <span class="id">{data.fileVersion.id}</span>
                <span class="user">by {data.fileVersion.user}</span>
            </div>
        </div>

        <div class="actions">
            <a class="history" href="{data.fileVersion.id}/history">history</a>

            {#if !data.fileVersion.deleted}
                <a href="{data.publicApi}/{data.repository.name}/fileversion/{data.fileVersion.id}/file" target="_blank"
                   class="download">download</a>
            {/if}
        </div>
    </div>

    <div class="content">
        {#if data.fileVersion.deleted}
            <div class="deleted">This file was deleted</div>
        {:else if ['png', 'jpg', 'gif'].includes(data.fileVersion.fileName.split('.')[1])}
            <ImagePreview {data}/>
        {:else if ['pdf'].includes(data.fileVersion.fileName.split('.')[1])}
            <PdfPreview {data}/>
        {:else}
            <TextPreview {data}/>
        {/if}
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

    .info {
        display: flex;
        align-items: center;
    }

    img {
        width: 25px;
        margin-right: .75em;
    }

    .id {
        text-decoration: underline;
        text-decoration-color: cornflowerblue;
        text-decoration-thickness: 2px;
    }

    .user {
        color: #b3b3b3;
    }

    a, a:visited {
        text-decoration: none;
        color: #fff;
    }

    .actions {
        width: 20%;
        display: flex;
        justify-content: space-between;
    }

    .download {
        color: #f2751b !important;
    }

    .history {
        color: cornflowerblue !important;
    }

    .content {
        background-color: #1e1e1e;
        border-radius: 0 0 8px 8px;
    }

    .deleted {
        margin: .5em;
    }
</style>
