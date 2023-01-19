# gum
Git UniMproved (like Vi IMproved)\
Just a simple version control with a minimal resemblance to git. Just for a small study project.

# How does gum work?
## Basic idea
```
          __________________________________________________
         /                                                  |
        /  Hi, my name is Gowl! I will help you using gum.  |
 /\ /\ /____________________________________________________|
((ovo))
():::()
  VVV
```
gum, pronounced `goohm`, is a simple version control with a cli client and a remote server. \
First of all, we do not think in terms of files, but in terms of file versions, located in a repository. A file version is, as the name suggest a specific version of a file on your file system identified by its file name. \
Further you still have to know that we structure our file version in specific tags. A tag is collection of file versions. The collection can only contain one file version per file name. But, just like files, tags can be modified. Because of that we do not persist a immutable tag. Instead we save tag versions in our database. \
You think that was all? You are wrong. Just one more thing, I promise! Let me introduce you to locks. These are basically locking files or tags, so only the person who locks them, can publish a new version. If you do not own the lock, you are out of luck, buddy. \
Everything alright mate?

## CLI
Ok, let us look at the CLI. The CLI is the best way to interact with a gum server. There is also a webclient, but you only need it to create a new repo. So I think you agree, that we want to use the CLI, right? \
Before I start explanining the exact functionalities, you should understand what will happen. When you initialise a repo locally, gum will create the magic `.gum` folder. In this folder there is a config file. You should leave it alone! Actually you can forget everything I said, but I wanted to say it at least once. Psssst, last night I heard a bearded mystical being say that you can change your identity by manipulating this file. Pretty creepy!

*Let's get into the cli and use gum!*
```
docker run -it --rm ghcr.io/dliebg/gum/cli:main
```

### init
The first thing you have to do, is to initialise a repository from a remote server. You can do it with the following command, but please change the URL of your upstream.
```
gum init https://gum.benedikt-schwering.de/api/repo0
```

### info
Alright! Your repository is initialised and now you want to check for changes, both local and remote. Do it with the info command. Untracked files will not be shown.
```
gum info
```

### sync
Since the initialisation there could be new versions of a file on the remote. To download these changes just run the following command. You can also define a tag you wish to download. But keep in mind that all your dangling files will be overwritten when you do not specify specific files for the synchronisation.
```
gum sync
```
```
gum sync -t tag-name
```
```
gum sync -f file-name
```

### tag
Tags are wonderful. All files in the repo are tagged. You can create a new tag with all your local files.
```
gum tag -t tag-name
```
Another way of creating a tag with your current files, is by adding the -h parameter. Your tag will be hard, so you can not change it anymore. In the background we will create a normal tag and put a immutable lock on the tag. Pretty easy.
```
gum tag -t tag-name -h
```

### update
Imagine you made changes to a local file. How do we can upload it to the server? For that we have to use the update command. It will automatically create a new file version and upload the file contents into the remote database. I will ask you to double check your upload, so we can avoid silly modifications.
```
gum update
```
Maybe you are also a very hard one, then you can skip the double check with the parameter -y.
```
gum update -y
```
To only update a specific file, please add the -f parameter.
```
gum update -f file-name
```

### delete
Do not delete files through the delete function of your file system. It is alot easier if gum does that for you. It also will persist the deletion to the database. Run the delete command with the -f parameter for the file name.
```
gum delete -f file-name
```

### lock
Some people think that you can avoid concurrency by adding locks. Oh well, we can do that too. You can lock a file (in all tags) with this command.
```
gum lock -f file-name-prefix
```
But you can also lock a whole tag. We do not want to combine both variants. This would be quite confusing.
```
gum lock -t tag-name-prefix
```

### unlock
Locks are the only thing you can really delete in gum. So we need one more command. The unlock command uses the lock id, which you have to look up with the lock command leaving out the parameters.
```
gum unlock lock-id
```

### history
I always talk about version control. But here is the first useful version control feature. You can view the history or as we call it, the file versions, of a file, by using the following command.
```
gum history -f file-name
```
Similar to the files, the command also works for tags.
```
gum history -t tag-name
```

### recall
If you found a file version, you want to recover, you can use the recall command.
```
gum recall file-version-id
```
But if you wish to recall a whole tag version, you can do that too.
```
gum recall tag-version-id
```

### diff
Sometimes you want to keep track of changes in your files. So you can see the delat by using the diff command. You have to provide two file version ids.
```
gum diff file-version-id file-version-id
```
Thats quite annoying. If you want to quickly lookup changes between a local file and the current upstream version, just pass in the file name through the -f parameter.
```
gum diff -f file-name
```

<br><br><br>
```
          __________________________________________________________
         /                                                          |
        /  Pretty late! I have to go and catch a mouse for dinner.  |
 /\ /\ /____________________________________________________________|
((ovo))
():::()
  VVV
```
