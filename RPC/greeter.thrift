namespace java fr.insa.tc.greeter
namespace go greeter
namespace js greeter

struct Contact {
  1: string firstname;
  2: string lastname;
  3: optional string email;
}

struct Greeting {
  1: string message;
}

service Greeter {
  Greeting greet(1: Contact contact)
}

