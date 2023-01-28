<script>
    export let data;
</script>

<div class="body">
    <iframe src="{data.publicApi}/{data.repository.name}/fileversion/{data.fileVersion.id}/preview" class="pdf"/>
</div>

<style>
    .body {
        display: flex;
        height: 600px;
    }

    .pdf {
        width: 100%;
        border: none;
        border-radius: 0 0 8px 8px;
    }
</style>
