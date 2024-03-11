import { ComponentFixture, TestBed } from "@angular/core/testing";
import { PonderationComponent } from "./ponderation.component";


describe('PonderationComponent', () => {
  let component: PonderationComponent;
  let fixture: ComponentFixture<PonderationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PonderationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PonderationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
