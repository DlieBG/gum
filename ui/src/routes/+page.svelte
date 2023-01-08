<script>
    import RepositoryCard from "./RepositoryCard.svelte";
    import {fade} from 'svelte/transition';

    export let data;
</script>

<div class="body">
    <h1>Welcome to gum</h1>

    <p>
        First of all, select a repository or <a class="link" href="new">create</a> a new one.
    </p>

    <div class="list">
        {#each data.repositories as repository (repository.id)}
            <div class="list-item" in:fade>
                <a href="{repository.name}/tag/main">
                    <RepositoryCard {repository}/>
                </a>
            </div>
        {/each}
    </div>
</div>

<style>
    .body {
        margin: 1em;
    }

    a, a:visited {
        text-decoration: none;
        color: #fff;
    }

    .link {
        color: #f2751b !important;
    }

    .list {
        margin-top: 32px;
        display: flex;
        flex-direction: row;
        flex-wrap: wrap;
        justify-content: space-evenly;
        align-items: stretch;
    }

    .list-item {
        margin: 1em;
    }
</style>
