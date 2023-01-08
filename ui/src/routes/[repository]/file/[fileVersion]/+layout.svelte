<script>
    export let data;
</script>

<div class="body">
    <div class="header">
        <h3>{data.fileVersion.fileName}</h3>
    </div>

    <div class="content">
        <slot/>
    </div>
</div>

<style>
    .header {
        margin-top: 1em;
    }
</style>
