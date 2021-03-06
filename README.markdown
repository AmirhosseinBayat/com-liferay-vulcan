# Vulcan, may your APIs live long and prosper

## The Vulcan project

The Vulcan project provides a set of Guidelines and Software to build APIs and consumers designed to evolve.

### [Vulcan Guidelines](https://vulcan.wedeploy.io/guidelines/)

An opinionated way to do RESTful APIs for evolvability and discoverability. Evolvability means that it’s easy to evolve the API without breaking consumers. Discoverability is even more exciting. In Vulcan APIs, the provider controls the navigation, forms, state changes… which simplifies the consumers and allows them to "learn" certain new functionalities that didn’t exist when they were developed. Sounds like magic and it’s indeed pretty cool.

The two key secrets that make this possible are:

**Hypermedia**: yeah, the good old links and forms that we all use through a browser every day can also be applied to APIs to get the same great decoupling and flexibility.

**Shared Vocabularies**: instead of returning a JSON/XML with attributes tied to the names of the internal models, use standard vocabularies that are well thought out by standardization bodies (such as [schema.org](https://schema.org) or [IANA](https://www.iana.org/assignments/link-relations/link-relations.xhtml)). Even if you have to create your own types (because a standard doesn’t exist), define it explicitly to be decoupled from any changes that you can make to the internal model.

And there are other additional goodies such as:

The consumer can control what the response will include: which fields, embedded resources, ...

The consumer can decide which hypermedia format fits its needs best (HAL, JSON-LD, etc.),

### [Vulcan Architect](https://github.com/liferay/com-liferay-vulcan)

A server-side library to facilitate the creation of Vulcan REST APIs. Vulcan Architect is opinionated to reduce the amount of code API developers has to write. This is achieved as well by implementing out of the box well known patterns in REST APIs such as the Collection Pattern.

With Vulcan Architect you can create APIs that follow all the principles of Vulcan Guidelines without much effort.

### Vulcan Consumers

A client-side library to facilitate developing consumers that consume Vulcan REST APIs (or any Hypermedia API, really). It also has some smart capabilities such as automatic creation of a local graph to facilitate building offline support.

- [Vulcan Consumer for Android](https://github.com/liferay-mobile/vulcan-consumer-android)
- Vulcan Consumer for iOS (coming...)
- Vulcan Consumer for JS (coming...)

## Why should I use it?

When creating a Hypermedia API there are many things you need to consider like representation format, relations between resources, vocabularies, etc. Because of this, Architect was built as a library that facilitates developing a Hypermedia API that follows all the principles while worrying only about your internal logic.

So you can focus on creating beautiful APIs that will live longer and prosper.

## How will it help me?

Architect provides JAX-RS writers for the most important Hypermedia formats, such as HAL or JSON-LD (with more coming). It also provides an easy way of representing your resources, in a really generic way, so every representation can understand it, but following common Hypermedia patterns, such as the Representor.

Also provides a really simple way of creating the different endpoints for your resources, which has many similarities with the JAX-RS approach. So migrating your API from a REST JAX-RS implementation to Architect will be easy as pie.

## How do I start using it?

Start creating your first API with Architect is very simple. All you need is an OSGi container with JAX-RS.

If you just want to try all this quickly, you can use our [docker image](https://hub.docker.com/r/ahdezma/vulcan-whiteboard/). Simply run this on your terminal (specifying the folder where to do module hot-deploying):

```
docker run -p 8080:8080 -v "/Users/YOUR_USER/deploy:/deploy" -d ahdezma/vulcan-whiteboard
```

As simple as that, you will have a JAX-RS application with Vulcan Architect running in an OSGi container, which you can consult by making a request to `http://localhost:8080`.

Now just add these lines to your `pom.xml`:

```xml
<dependency>
  <groupId>com.liferay</groupId>
  <artifactId>com.liferay.vulcan.api</artifactId>
  <version>LATEST</version>
</dependency>
```

or `build.gradle`:

```groovy
dependencies {
	provided group: "com.liferay", name: "com.liferay.vulcan.api", version: "LATEST"
}
```

And you're ready to create your first Vulcan Architect resource!

Create a new Java class and annotate it with `@Component` to expose it as an OSGi component. Then have it implement the [`CollectionResource`](https://github.com/liferay/com-liferay-vulcan/blob/master/vulcan-api/src/main/java/com/liferay/vulcan/resource/CollectionResource.java) class of `vulcan-api`. You will have to provide two type arguments: the type of the model you want to expose, and the type of identifier that uses that model, for example [`LongIdentifier`](https://github.com/liferay/com-liferay-vulcan/blob/master/vulcan-api/src/main/java/com/liferay/vulcan/resource/identifier/LongIdentifier.java) (if your type uses a long number internally as an identifier).

Now you will simply have to implement three methods:

`getName`: to provide the name for this resource. This name is used internally in Architect for different tasks, including URL creation.

`buildRepresentor`: to build the mapping between your internal model and the standard vocabulary you have chosen (e.g. [schema.org](https://schema.org).

`routes`: to build the mapping between the operations supported for this resource and the methods that Architect should call to complete them.

And that's it! Build the `jar` of your module and deploy it in the folder you declared to make hot deployments, wait for it to activate... and that's it!

If you make a request to `http://localhost:8080` again, you should see a new declared endpoint corresponding to the new resource you have just created.

## What if I just want to try this "Vulcan APIs"?

However, if you don't want to create your own API for now, but just want to try all this Hypermedia, Shared Vocabularies, Vulcan APIs, etc., you can use our test server for that.

As simply as use your favorite REST-request client to make a GET request to:

`http://vulcan-vulcansample.wedeploy.io`

To be able to use Vulcan Architect APIs you must specify an `accept` HTTP header. If you want to try a Hypermedia representation format, you can start with:

`accept: application/ld+json`

to order JSON-LD or:

`accept: application/hal+json`

to order HAL.

And start surfing the Hypermedia world!