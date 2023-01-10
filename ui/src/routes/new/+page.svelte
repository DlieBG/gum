<script>
    import {enhance} from '$app/forms';
</script>

<div class="body">
    <h1>Create new Repository</h1>

    <div class="content">
        <form method="POST" use:enhance>
            <label>
                Repository Name:
                <input name="name"/>
            </label>

            <div class="actions">
                <button class="create" type="submit">
                    Create
                </button>

                <a href="/">
                    <button class="cancel">Cancel</button>
                </a>
            </div>
        </form>
    </div>
</div>

<style>
    .body {
        margin: 1em;
    }

    .content {
        margin: 3em 0 2em 2em;
    }

    .actions {
        margin-top: 2em;
    }

    button {
        border: none;
        border-radius: 8px;
        background-color: inherit;
        padding: 14px 28px;
        font-size: 16px;
        cursor: pointer;
        display: inline-block;
    }

    button:hover {
        background-color: #242332;
    }

    a, a:visited {
        text-decoration: none;
        color: #fff;
    }

    .create {
        color: #f2751b;
    }

    .cancel {
        color: crimson;
    }
</style>
