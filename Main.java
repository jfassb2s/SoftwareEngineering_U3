import uebung3.*;

class Main
{
    public static void main(String[] args) {
        Container container = new Container();
        MemberView memberView = new MemberView();

        Member m1337 = new MemberKonkret(1337);
        Member m42 = new MemberKonkret(42);
        Member m99 = new MemberKonkret(99);

        try
        {
            container.addMember(m1337);
            container.addMember(m42);
            container.addMember(m99);
            container.addMember(new MemberKonkret(1));
        } catch (ContainerException e) {

        }



        memberView.dump(container.getCurrentList());
    }
}