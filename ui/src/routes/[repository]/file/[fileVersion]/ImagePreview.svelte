<script>
    export let data;
</script>

<div class="body">
    <img src="{data.publicApi}/{data.repository.name}/fileversion/{data.fileVersion.id}/file" class="image"/>
</div>

<style>
    .body {
        display: flex;
    }

    .image {
        width: 100%;
        border-radius: 0 0 8px 8px;
    }
</style>
