<script>
    export let repository;
</script>

<div class="body">
    <img src={repository.avatar}>
    <h2>{repository.name}</h2>
</div>

<style>
    .body {
        border: 1px solid forestgreen;
        border-radius: 12px;
        width: 250px;
        height: 100%;
        display: flex;
        align-items: center;
        cursor: pointer;
    }

    img {
        width: 50px;
        margin: 1em 0 1em 1em;
    }

    h2 {
        margin: 1em
    }
</style>
