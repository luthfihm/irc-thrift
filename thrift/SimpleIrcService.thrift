service SimpleIrcService
{
	string login(),
	string nick(1:string name, 2:string new_name),
	string join(1:string name, 2:string channel),
	string leave(1:string name, 2:string channel),
	string exit(1:string name),
	string sendToAllChannel(1:string name, 2:string message),
	string sendToChannel(1:string name, 2:string channel, 3:string message),
	list<string> getMessage(1:string name, 2:string channel),
} 